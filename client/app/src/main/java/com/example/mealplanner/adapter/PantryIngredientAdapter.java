package com.example.mealplanner.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealplanner.R;
import com.example.mealplanner.model.PantryIngredient;
import com.example.mealplanner.service.APIClient;
import com.example.mealplanner.service.APIService;
import com.example.mealplanner.wrapper.PantryIngredientWrapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PantryIngredientAdapter extends RecyclerView.Adapter<PantryIngredientViewHolder> {
    private List<PantryIngredientWrapper> mList;
    //private ArrayList<PantryIngredient> nestedList = new ArrayList<>();
    private Context context;
    private APIClient APIClient = new APIClient();
    private Retrofit retrofit = APIClient.getClient();
    private APIService service = retrofit.create(APIService.class);
    private PantryIngredient changedIngredient = null;
    private RecyclerView mRecyclerView;



    public PantryIngredientAdapter(List<PantryIngredientWrapper> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }
    @Override
    public PantryIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_category_item, parent, false);
        return  new PantryIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryIngredientViewHolder holder, int position) {
        PantryIngredientWrapper pantryIngredientWrapper = mList.get(position);
        holder.getTextView().setText(pantryIngredientWrapper.getItemText());
        holder.getTotalAmount().setText(pantryIngredientWrapper.getTotalAmount().toString());
        holder.getTotalAmountType().setText(pantryIngredientWrapper.getTotalAmountType());
        boolean isExpandable = pantryIngredientWrapper.isExpandable();
        holder.getExpandableLayout().setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        if(isExpandable){
            holder.getArrowImage().setImageResource(R.drawable.arrow_up);
        }else{
            holder.getArrowImage().setImageResource(R.drawable.arrow_down);
        }
        NestedPantryIngredientAdapter nestedAdapter = new NestedPantryIngredientAdapter(mList.get(position).getNestedList());

        nestedAdapter.setOnItemsChangedListener(new NestedPantryIngredientAdapter.OnItemsChangedListener() {
            @Override
            public void onDateChanged(Editable editable, int p) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "dd/MM/yy" , Locale.UK );
                String date = editable.toString();
                LocalDate ld = LocalDate.parse(date, formatter);
                PantryIngredient pantryIngredient = mList.get(holder.getAdapterPosition()).getNestedList().get(p);
                pantryIngredient.setExpirationDate(ld);
                saveChanges(pantryIngredient, holder.getAdapterPosition(), p);
            }

            @Override
            public void onAmountChanged(String amount, int p) {
                PantryIngredient pantryIngredient = mList.get(holder.getAdapterPosition()).getNestedList().get(p);
                BigDecimal old = pantryIngredient.getAmount();
                pantryIngredient.setAmount(new BigDecimal(amount));
                saveChanges(pantryIngredient, holder.getAdapterPosition(),p);
                BigDecimal curr = new BigDecimal(amount).subtract(old);
                mList.get(holder.getAdapterPosition()).setTotalAmount(mList.get(holder.getAdapterPosition()).getTotalAmount().add(curr));
                if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                    notifyItemChanged(holder.getAdapterPosition());
                }
            }

            @Override
            public void onLongItemClick(View view, int p) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.deletePantry){
                            int id = mList.get(holder.getAdapterPosition()).getNestedList().get(p).getId();
                            BigDecimal amount = mList.get(holder.getAdapterPosition()).getNestedList().get(p).getAmount();
                            deleteIngredient(id);
                            mList.get(holder.getAdapterPosition()).getNestedList().remove(p);
                            nestedAdapter.notifyItemRemoved(p);
                            if(mList.get(holder.getAdapterPosition()).getNestedList().isEmpty()){
                                mList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }else{
                                BigDecimal newAmnt = mList.get(holder.getAdapterPosition()).getTotalAmount().subtract(amount);
                                mList.get(holder.getAdapterPosition()).setTotalAmount(newAmnt);
                                if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                                    notifyItemChanged(holder.getAdapterPosition());
                                }
                            }

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        holder.getNestedRecyclerView().setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.getNestedRecyclerView().setHasFixedSize(true);
        holder.getNestedRecyclerView().setAdapter(nestedAdapter);
        holder.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantryIngredientWrapper.setExpandable(!pantryIngredientWrapper.isExpandable());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    private void deleteIngredient(int id){
        Call<ResponseBody> deleteCall = service.deletePantryIngredient(id);
        deleteCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT);
            }
        });
    }
    private void saveChanges(PantryIngredient pantryIngredient, int parentP, int p){
        Call<ResponseBody> pantryCall = service.updatePantryIngredient(pantryIngredient.getId(),pantryIngredient);
        pantryCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    mList.get(parentP).getNestedList().set(p, pantryIngredient);
                }else{
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private BigDecimal getTotalAmount(int position){
        BigDecimal amount = new BigDecimal(0);
        for(PantryIngredient ing : mList.get(position).getNestedList()){
            amount.add(ing.getAmount());
        }
        return amount;
    }

}
