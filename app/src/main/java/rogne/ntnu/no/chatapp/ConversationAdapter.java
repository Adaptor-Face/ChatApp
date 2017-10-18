package rogne.ntnu.no.chatapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristoffer on 2017-10-18.
 */

class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {
    List<Conversation> convos = new ArrayList<>();
    private final Context context;
    OnClickListener listener;

    public interface OnClickListener{
        void onClick(int position);
    }
    public ConversationAdapter(Context context){ this.context = context;}


    @Override
    public ConversationAdapter.ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation, parent, false);
        return new ConversationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConversationAdapter.ConversationViewHolder holder, int position) {

    }
    public void setListener(OnClickListener listener){this.listener = listener;}
    public List<Conversation> getConvos(){return convos;}
    public void setConvos(List<Conversation> convos){
        this.convos = convos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder{
        public CardView cv;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.conversation_card);
        }
    }
}
