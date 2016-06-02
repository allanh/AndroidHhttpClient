package com.fuhu.test.smarthub.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fuhu.test.smarthub.R;
import com.fuhu.test.smarthub.componet.ChannelItem.RtpChannel;

import java.util.List;

public class RTPPeerAdapter extends ArrayAdapter<RtpChannel> {
    private static final long FADE_TIMEOUT = 3000;

    private final Context context;
    private LayoutInflater inflater;
    private List<RtpChannel> values;

    public RTPPeerAdapter(Context context, List<RtpChannel> values) {
        super(context, R.layout.peer_row_layout, android.R.id.text1, values);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.values=values;
    }

    private class ViewHolder {
        TextView channelKey;
        TextView uuids;
        TextView occupancy;
        RtpChannel rtpChannel;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        RtpChannel rtpChannel;
        if(position >= values.size()){ rtpChannel = new RtpChannel(); } // Catch Edge Case
        else { rtpChannel = this.values.get(position); }

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.peer_row_layout, parent, false);
            holder.channelKey = (TextView) convertView.findViewById(R.id.channel);
            holder.uuids = (TextView) convertView.findViewById(R.id.uuids);
            holder.occupancy = (TextView) convertView.findViewById(R.id.occupancy);
            convertView.setTag(holder);
            Log.d("Adapter", "Recreating fadeout.");
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.channelKey.setText(rtpChannel.getKey());
        holder.uuids.setText(rtpChannel.getUuidsString());
        holder.occupancy.setText(String.valueOf(rtpChannel.getOccupancy()));
        holder.rtpChannel=rtpChannel;
        return convertView;
    }

    @Override
    public int getCount() {
        return this.values.size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position){
        if (position >= values.size()){ return -1; }
        return values.get(position).hashCode();
    }

    public void setChannels(List<RtpChannel> values) {
        if (values != null) {
            this.values = values;
            notifyDataSetChanged();
        }
    }

    @Override
    public RtpChannel getItem(int position) {
        if (position >= values.size()){ return null; }
        return values.get(position);
    }
}