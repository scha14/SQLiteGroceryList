package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import grocerylist.sqlite.app.jsmtech.sqlitegrocerylist.R;
import model.Grocery;
import sqlite.DBAdapter;

/**
 * Created by Sukriti on 6/16/16.
 */
public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.RV_ViewHolder> {

    private List<model.Grocery> listOfItems;
    private Context mContext;
    View itemView;
    private DBAdapter dbAdapter;



    public GroceryAdapter(Context context, ArrayList<Grocery> listOfGroceries) {
        this.mContext = context;
        this.listOfItems = listOfGroceries;
        dbAdapter = new DBAdapter(mContext);
    }

    @Override
    public RV_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grocery_item_view, parent, false); // link to xml
        return new RV_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RV_ViewHolder holder, int position) {
        final Grocery grocery  = listOfItems.get(holder.getAdapterPosition());
        holder.mItem.setText(grocery.getItem());
        // Toast.makeText(mContext, isChecked + "", Toast.LENGTH_SHORT).show();;

        // holder.mChecked.setVisibility(View.GONE);
        if(!grocery.getIsVisited()) {
            grocery.setIsVisited(true);
            holder.mChecked.setChecked(false);
            holder.mChecked.setOnCheckedChangeListener(null);

            if(listOfItems.get(holder.getAdapterPosition()).getChecked() == 1) {
                holder.mChecked.setChecked(true);
            } else if(listOfItems.get(holder.getAdapterPosition()).getChecked() == 0) {
                holder.mChecked.setChecked(false);
            }

            holder.mChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Grocery c = listOfItems.get(holder.getAdapterPosition());

                    dbAdapter.open();
                    if(isChecked) {
                        c.setChecked(1);
                    }
                    else {
                        c.setChecked(0);
                    }
                    dbAdapter.updateGroceryItem(listOfItems.get(holder.getAdapterPosition()).getId(), c);

                    dbAdapter.close();


                }
            });


        } else if(grocery.getIsVisited()) {

            if(listOfItems.get(holder.getAdapterPosition()).getChecked() == 1) {
                holder.mChecked.setChecked(true);
            } else if(listOfItems.get(holder.getAdapterPosition()).getChecked() == 0) {
                holder.mChecked.setChecked(false);
            }

            holder.mChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Grocery c = listOfItems.get(holder.getAdapterPosition());

                    dbAdapter.open();
                    if(isChecked) {
                        c.setChecked(1);
                    }
                    else {
                        c.setChecked(0);
                    }
                    dbAdapter.updateGroceryItem(listOfItems.get(holder.getAdapterPosition()).getId(), c);
                    dbAdapter.close();

                }
            });
        }

        if(listOfItems.get(holder.getAdapterPosition()).getChecked() == 1) {
            holder.mChecked.setChecked(true);
        } else if(listOfItems.get(holder.getAdapterPosition()).getChecked() == 0) {
            holder.mChecked.setChecked(false);
        }

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbAdapter.open();

                dbAdapter.deleteGroceries(grocery.getId());


                dbAdapter.close();

                // We have deleted from the database!

                // We will also have to update the UI!
                listOfItems.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            }
        });

//        holder.mChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                Grocery c = listOfItems.get(holder.getAdapterPosition());
//
//                dbAdapter.open();
//                if(isChecked) {
//                    c.setChecked(1);
//                }
//                else {
//                    c.setChecked(0);
//                }
//                dbAdapter.updateGroceryItem(listOfItems.get(holder.getAdapterPosition()).getId(), c);
//
//                dbAdapter.close();
//
//
//            }
//        });
    }



    @Override
    public int getItemCount() {
        return listOfItems.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class RV_ViewHolder extends RecyclerView.ViewHolder {

        protected TextView mItem;
        protected CheckBox mChecked;
        protected ImageButton mDelete;

        public RV_ViewHolder(View itemView) {
            super(itemView);

            mItem = (TextView) itemView.findViewById(R.id.name);
            mChecked = (CheckBox) itemView.findViewById(R.id.checked);
            mDelete = (ImageButton) itemView.findViewById(R.id.delete);


        }


    }
}
