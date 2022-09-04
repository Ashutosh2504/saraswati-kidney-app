package com.example.healthifyapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthifyapp.R;

import com.example.healthifyapp.model.MedicalConditionDataModel;
import com.example.healthifyapp.report.Root;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyviewHolder> {

    Context context;

    List<Root.Result.DietAnalysisDetails> dietAnalysisReport;

    public ReportAdapter(Context context, List<Root.Result.DietAnalysisDetails> dietAnalysisReport) {
        this.context = context;
        this.dietAnalysisReport = dietAnalysisReport;
    }
    public void setDietAnalysisObjectList(List<Root.Result.DietAnalysisDetails> dietAnalysisReport) {
        this.dietAnalysisReport = dietAnalysisReport;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ReportAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_lunch_report, parent, false);
        return new MyviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.MyviewHolder holder, int position) {

      try
      {
         // Root.Result.DietAnalysisDetails dietAnalysisDetails = dietAnalysisReport.get(position);
          holder.item.setText(dietAnalysisReport.get(position).getItem());
          holder.calorie.setText(dietAnalysisReport.get(position).getkCal());
          holder.date.setText(dietAnalysisReport.get(position).getDate());
          holder.quantity.setText(dietAnalysisReport.get(position).getQuantity());
          holder.time.setText(dietAnalysisReport.get(position).getTime());
      }
      catch (Exception e)
      {
          Log.d("Reporrrtrtrtrt","::"+e.getMessage());
      }
    }

    @Override
    public int getItemCount() {
        return dietAnalysisReport.size();
    }



    protected class MyviewHolder extends RecyclerView.ViewHolder {
        TextView item, quantity, calorie, date, time;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.item_report);
            quantity = (TextView) itemView.findViewById(R.id.quantity_report);
            calorie = (TextView) itemView.findViewById(R.id.kcal_report);
            date = (TextView) itemView.findViewById(R.id.date_report);
            time = (TextView) itemView.findViewById(R.id.time_report);
        }
    }
}