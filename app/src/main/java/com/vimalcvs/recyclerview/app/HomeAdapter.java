package com.vimalcvs.recyclerview.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vimalcvs.recyclerview.R;
import com.vimalcvs.recyclerview.SecondActivity;
import com.vimalcvs.recyclerview.models.HomeResult;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.InvitationHolder> {

    private final Context context;
    private final List<HomeResult> homeResults;
    private final HomeOnRecordEventListener listener;

    public HomeAdapter(Context context, List<HomeResult> homeResults, HomeOnRecordEventListener listener) {
        this.context = context;
        this.homeResults = homeResults;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InvitationHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);

        return new InvitationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitationHolder holder, int position) {
        HomeResult planet = homeResults.get(position);
        holder.setDetails(planet);



    }

    @Override
    public int getItemCount() {
        return homeResults.size();
    }

    class InvitationHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDeclined, txtAccepted;
        ImageButton btn_decline, btn_accept;
        CircleImageView img;
        CardView card_view;

        InvitationHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            img = itemView.findViewById(R.id.img);
            card_view =itemView.findViewById(R.id.card_view);
            btn_accept = itemView.findViewById(R.id.btn_accept);
            btn_decline = itemView.findViewById(R.id.btn_decline);
            txtAccepted = itemView.findViewById(R.id.txtAccepted);
            txtDeclined = itemView.findViewById(R.id.txtDeclined);
        }

        void setDetails(final HomeResult homeResult) {
            txtName.setText(String.format(homeResult.getHomeName().getFirst()));


            btn_accept.setOnClickListener(v -> {
                listener.accept(homeResult);
                homeResult.setStatus(HomeConstants.ACCEPT);
                setVisibility(homeResult);
            });


            btn_decline.setOnClickListener(v -> {
                listener.decline(homeResult);
                homeResult.setStatus(HomeConstants.DECLINE);
                setVisibility(homeResult);
            });

            card_view.setOnClickListener(v -> {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("name",String.format(homeResult.getHomeName().getFirst()));
                context.startActivity(intent);
            });


            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(homeResult.getHomePicture().getThumbnail())
                    .placeholder(R.drawable.other)
                    .error(R.drawable.other)
                    .into(img);

            setVisibility(homeResult);

        }

        void setVisibility(HomeResult homeResult) {
            switch (homeResult.getStatus()) {
                case HomeConstants.PENDING:
                    txtDeclined.setVisibility(View.GONE);
                    txtAccepted.setVisibility(View.GONE);
                    btn_decline.setVisibility(View.VISIBLE);
                    btn_accept.setVisibility(View.VISIBLE);
                    break;
                case HomeConstants.ACCEPT:
                    txtDeclined.setVisibility(View.GONE);
                    txtAccepted.setVisibility(View.VISIBLE);
                    btn_decline.setVisibility(View.GONE);
                    btn_accept.setVisibility(View.GONE);
                    break;
                case HomeConstants.DECLINE:
                    txtDeclined.setVisibility(View.VISIBLE);
                    txtAccepted.setVisibility(View.GONE);
                    btn_decline.setVisibility(View.GONE);
                    btn_accept.setVisibility(View.GONE);
                    break;
            }

        }

    }

}
