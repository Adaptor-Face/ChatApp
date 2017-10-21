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

import rogne.ntnu.no.chatapp.Data.Message;
import rogne.ntnu.no.chatapp.R;

/**
 * Created by krist on 2017-10-21.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messages = new ArrayList<>();
    private final Context context;
    OnClickListener listener;

    public interface OnClickListener {
        void onClick(int position);
    }

    public MessageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_message, parent, false);
                break;
            case 0:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.their_message, parent, false);
                break;
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.their_message, parent, false);
        }
        return new MessageViewHolder(itemView, viewType == 1);
    }

    @Override
    public int getItemViewType(int position) {
        //TODO: Replace with actual username
        if (messages.get(position).getUser().equals("Tom")) {
            return 1;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MessageViewHolder holder, int position) {
        Message msg = messages.get(position);
        holder.message.setText(msg.getText());
        holder.username.setText(msg.getUser());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public void addMessage(Message message){
        this.messages.add(message);
        notifyDataSetChanged();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView message;
        TextView username;

        public MessageViewHolder(View itemView, boolean isMyMessage) {
            super(itemView);
            if (isMyMessage) {
                setMyStyle();
            } else {
                setTheirStyle();
            }
        }

        private void setTheirStyle() {
            this.message = itemView.findViewById(R.id.theirMessage);
            this.username = itemView.findViewById(R.id.theirUserName);
            this.cv = itemView.findViewById(R.id.theirCard);
        }

        private void setMyStyle() {
            this.message = itemView.findViewById(R.id.myMessage);
            this.username = itemView.findViewById(R.id.myUserName);
            this.cv = itemView.findViewById(R.id.myCard);
        }
    }
}
