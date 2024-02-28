package com.example.pjhappybank.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pjhappybank.Model.UserDetails;
import com.example.pjhappybank.ViewModel.FamilyViewModel;
import com.example.pjhappybank.R;

import java.util.ArrayList;
import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder> {

    private Context context;
    private List<UserDetails> familyMembers;

    public FamilyAdapter(Context context) {
        this.context = context;
        this.familyMembers = new ArrayList<>();
    }

    @NonNull
    @Override
    public FamilyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new FamilyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyViewHolder holder, int position) {
        UserDetails member = familyMembers.get(position);

        holder.positionTextView.setText(getPositionName(member.getPosition()));
        holder.nameTextView.setText(member.getName());
        holder.emotionImageView.setImageResource(getEmotionDrawableResourceId(member.getEmotion()));
        holder.personImageView.setImageResource(getPositionDrawableResourceId(member.getPosition()));
        holder.quantityTextView.setText(String.valueOf(member.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return familyMembers.size();
    }

    public void setFamilyMembers(List<UserDetails> familyMembers) {
        this.familyMembers = familyMembers;
        notifyDataSetChanged();
    }

    static class FamilyViewHolder extends RecyclerView.ViewHolder {
        ImageView personImageView;
        TextView positionTextView;
        TextView nameTextView;
        ImageView emotionImageView;
        TextView quantityTextView;

        FamilyViewHolder(@NonNull View itemView) {
            super(itemView);
            personImageView = itemView.findViewById(R.id.imageView_item_person);
            positionTextView = itemView.findViewById(R.id.position_item_person);
            nameTextView = itemView.findViewById(R.id.name_item_person);
            emotionImageView = itemView.findViewById(R.id.emotion_item_person);
            quantityTextView = itemView.findViewById(R.id.quantity_item_person);
        }
    }

    private int getEmotionDrawableResourceId(String emotion) {
        if (emotion != null) {
            switch (emotion) {
                case "happy":
                    return R.drawable.happy;
                case "excited":
                    return R.drawable.excited;
                case "angry":
                    return R.drawable.angry;
                case "normal":
                    return R.drawable.normal;
                case "sad":
                    return R.drawable.sad;
                default:
                    return R.drawable.normal;
            }
        } else {
            return R.drawable.normal;
        }
    }

    private int getPositionDrawableResourceId(String position) {
        if (position != null) {
            switch (position) {
            case "daughter":
                return R.drawable.daughter;
            case "son":
                return R.drawable.son;
            case "mother":
                return R.drawable.mother;
            case "father":
                return R.drawable.father;
            default:
                return R.drawable.normal;
        }
        } else {
            return R.drawable.normal;
        }


    }

    private String getPositionName(String position) {
        if (position != null) {
            switch (position) {
                case "daughter":
                    return "Con gái";
                case "son":
                    return "Con trai";
                case "mother":
                    return "Mẹ";
                case "father":
                    return "Bố";
                default:
                    return "Khác";
            }
        } else {
            return "Khác";
        }
    }


}

