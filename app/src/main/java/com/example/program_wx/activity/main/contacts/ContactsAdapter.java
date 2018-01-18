package com.example.program_wx.activity.main.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.program_wx.R;
import com.example.program_wx.entity.User;
import com.example.program_wx.utils.LogUtil;

import java.util.List;

/**
 * Created by YinTao on 2018/1/8.
 */

public class ContactsAdapter extends BaseAdapter
{
    private Context context;
    private List<User> userList;

    public ContactsAdapter(Context context, List<User> userList)
    {
        this.context = context;
        this.userList = userList;
        LogUtil.e("userList=" + userList);
    }

    @Override
    public int getCount()
    {
        return userList.size();
    }

    @Override
    public User getItem(int position)
    {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_list, null);
            holder = new ViewHolder();
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tvHeader = (TextView) convertView.findViewById(R.id.header);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = userList.get(position);
        holder.tvHeader.setVisibility(View.VISIBLE);
        String nick = user.getNick();
        String avatar = user.getAvatar();
        String initialLetter = user.getInitialLetter();
        String preLetter = position > 0 ? getItem(position - 1).getInitialLetter() : null;
        if (initialLetter.equals(preLetter))
        {
            holder.tvHeader.setVisibility(View.GONE);
        }
        else
        {
            holder.tvHeader.setVisibility(View.VISIBLE);
            holder.tvHeader.setText(initialLetter);
        }
        holder.nameTextView.setText(nick);
        Glide.with(context).load(avatar).diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_avatar).error(R.drawable.default_avatar).into(holder.iv_avatar);
        return convertView;
    }

    private class ViewHolder
    {
        ImageView iv_avatar;
        TextView nameTextView, tvHeader;
    }

}
