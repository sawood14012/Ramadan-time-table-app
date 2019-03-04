package com.example.sawood.cardvie;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {

    private List<Recycleritem> listitems;
    private Context mcontext;

    public Myadapter(List<Recycleritem> listitems, Context mcontext) {
        this.listitems = listitems;
        this.mcontext = mcontext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Recycleritem listitem = listitems.get(position);
        holder.texttittle.setText(listitem.getItem());
        holder.textDesc.setText(listitem.getDecription());
        holder.days.setText(listitem.getDays());
        holder.seh.setText(listitem.getSeh_time());
        holder.ift.setText(listitem.getIft_time());
        holder.mar.setText(listitem.getMark());

       /* holder.textopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(mcontext, holder.textopt);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.menu_item_save:
                                Toast.makeText(mcontext,"Saved",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.menu_item_del:
                                 listitems.remove(position);
                                 notifyDataSetChanged();
                                 Toast.makeText(mcontext,"Deleted",Toast.LENGTH_LONG).show();
                                 break;

                                 default:
                                     break;

                        }


                        return false;
                    }
                });

                popupMenu.show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
      public TextView texttittle,textDesc,days,seh,ift,mar;
      public ViewHolder(View itemview){
          super(itemview);
          texttittle = itemview.findViewById(R.id.text1);
         textDesc=itemview.findViewById(R.id.text2);
         days = itemview.findViewById(R.id.days);
         seh=itemview.findViewById(R.id.seh_time);
         ift = itemview.findViewById(R.id.ift_time);
          mar=itemview.findViewById(R.id.textView);
          //textopt=itemview.findViewById(R.id.texopt);

      }

    }


}
