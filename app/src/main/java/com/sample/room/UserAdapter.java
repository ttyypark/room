package com.sample.room;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.room.databinding.UserProfileBinding;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ProfileViewHolder>
        implements ItemTouchHelperListener {
//    private List<UserProfile> UserProfileList;
    private onItemClickListener mListener;
    private List<UserProfile> mItems = new ArrayList<>();
    private Context mContext;
    private int selectItemIndex = -1;

    private static final String IMAGEVIEW_TAG = "드래그 이미지";

    public interface onItemClickListener {
        void onItemClicked (UserProfile model);
    }


    public UserAdapter() {}

    public UserAdapter(List<UserProfile> mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
    }

    // 리스너 객체 전달함수
    public void setOnItemClickListener(onItemClickListener listener){
        this.mListener = listener;
    }

    public UserAdapter(onItemClickListener listener) {
        mListener = listener;
    }

    public void setItems(List<UserProfile> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        UserProfileBinding binding = UserProfileBinding.
//                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        UserProfileBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.user_profile, parent, false);

        ProfileViewHolder viewHolder = new ProfileViewHolder(binding);

        View mImg = (View) binding.getRoot();
        mImg.setTag(IMAGEVIEW_TAG);

        // ****
//        mImg.setOnLongClickListener(new LongClickListener());
//        mImg.setOnDragListener(new DragListener());

        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    final UserProfile item = mItems.get(viewHolder.getAdapterPosition());
                    mListener.onItemClicked(item);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        final UserProfile item = mItems.get(position);
        holder.bind(item);
//         holder.binding.setUser(item);        // ?
        // TODO : 데이터를 뷰홀더에 표시하시오
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    @Override
    public boolean onItemMove(int from_position, int to_position) {
        Log.e("onItemMove", "from: " + from_position + "to: " + to_position);
        //이동할 객체 저장
        UserProfile person = mItems.get(from_position);

        // 이동할 객체 삭제
        mItems.remove(from_position);
//        model.delete(model.mUserProfile, mainBinding);

        // 이동하고 싶은 position에 추가
        mItems.add(to_position,person);

        // Adapter에 데이터 이동알림
        notifyItemMoved(from_position,to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }



    class DragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            Drawable normalShape = v.getResources().getDrawable(
                    R.drawable.normal_shape, null);
            Drawable targetShape = v.getResources().getDrawable(
                    R.drawable.target_shape, null);

            // 이벤트 시작
            switch (event.getAction()) {

                // 이미지를 드래그 시작될때
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("DragClickListener", "ACTION_DRAG_STARTED");
                    break;

                // 드래그한 이미지를 옮길려는 지역으로 들어왔을때
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENTERED");
                    // 이미지가 들어왔다는 것을 알려주기 위해 배경이미지 변경
                    v.setBackground(targetShape);
                    break;

                // 드래그한 이미지가 영역을 빠져 나갈때
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DragClickListener", "ACTION_DRAG_EXITED");
                    v.setBackground(normalShape);
                    break;

                // 이미지를 드래그해서 드랍시켰을때
                case DragEvent.ACTION_DROP:
                    Log.d("DragClickListener", "ACTION_DROP");

                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);

                    Log.d("ACTION_DROP view:", view.toString());
                    Log.d("ACTION_DROP v:", v.toString());

//                    final UserProfile item = mItems.get(viewHolder.getAdapterPosition());

//                    if (v == findViewById(R.id.bottomlinear)) {
//                        View view = (View) event.getLocalState();
//                        ViewGroup viewgroup = (ViewGroup) view
//                                .getParent();
//                        viewgroup.removeView(view);
//
//                        LinearLayout containView = (LinearLayout) v;
//                        containView.addView(view);
//                        view.setVisibility(View.VISIBLE);
//
//                    }else if (v == findViewById(R.id.toplinear)) {
//                        View view = (View) event.getLocalState();
//                        ViewGroup viewgroup = (ViewGroup) view
//                                .getParent();
//                        viewgroup.removeView(view);
//
//                        LinearLayout containView = (LinearLayout) v;
//                        containView.addView(view);
//                        view.setVisibility(View.VISIBLE);
//
//                    }else {
//                        View view = (View) event.getLocalState();
//                        view.setVisibility(View.VISIBLE);
//                        Context context = getApplicationContext();
//                        Toast.makeText(context,
//                                "이미지를 다른 지역에 드랍할수 없습니다.",
//                                Toast.LENGTH_LONG).show();
//                        break;
//                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENDED");
                    v.setBackground(normalShape); // go back to normal shape

                default:
                    break;

            }
            return true;
        }
    }

    private final class LongClickListener implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v) {
            // 태그 생성
            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN };
            ClipData data = new ClipData(v.getTag().toString(),
                    mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

            v.startDragAndDrop(data, shadowBuilder, v, 0);

            v.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        UserProfileBinding binding;

//        // TODO : 뷰홀더 완성하시오
//        private ImageView mImgPhoto;
//        .....................
//

        public ProfileViewHolder(UserProfileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(UserProfile userProfile){
            binding.setVariable(BR.user, userProfile);
        }
    }
}
