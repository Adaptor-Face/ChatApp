package rogne.ntnu.no.chatapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rogne.ntnu.no.chatapp.Activites.MainActivity;
import rogne.ntnu.no.chatapp.Data.Conversation;
import rogne.ntnu.no.chatapp.Data.Message;
import rogne.ntnu.no.chatapp.R;

/**
 * Created by Kristoffer on 2017-10-18.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder> {
    List<Conversation> conversations = new ArrayList<>();
    private final Context context;
    OnClickListener listener;

    public interface OnClickListener {
        void onClick(int position);
    }

    public ConversationAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ConversationAdapter.ConversationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation, parent, false);
        return new ConversationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConversationAdapter.ConversationViewHolder holder, int position) {
        Conversation conversation = conversations.get(position);
        holder.conversationParticipants.setText("");
        String conversationTitle = conversation.getParticipants(MainActivity.USERNAME);
        holder.conversationParticipants.append(conversationTitle);
        Message m = conversation.getLastMessage();
        if(m != null) {
            holder.lastMessage.setText(m.toMessageString().replace(MainActivity.USERNAME, "You"));
        } else {
            holder.lastMessage.setText("No messages to show.");
        }

    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void addConversation(Conversation conversation){
        this.conversations.add(conversation);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder {
        //TODO: Improve visuals
        public TextView conversationParticipants;
        public TextView lastMessage;
        public CardView cv;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            this.conversationParticipants = itemView.findViewById(R.id.conversation_card_text);
            this.lastMessage = itemView.findViewById(R.id.conversation_last_message);
            this.cv = itemView.findViewById(R.id.conversation_card);
            itemView.setOnClickListener(view -> {
                if(listener != null) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
