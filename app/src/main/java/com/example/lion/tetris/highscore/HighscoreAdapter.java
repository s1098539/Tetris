package com.example.lion.tetris.highscore;

/**
 * Created by lion on 16/03/2018.
 */
        import android.graphics.Typeface;
        import android.graphics.drawable.Drawable;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.lion.tetris.R;

        import java.util.List;

public class HighscoreAdapter extends RecyclerView.Adapter<HighscoreAdapter.MyViewHolder> {

    private List<Highscore> highscoreList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, score, level, date;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.imageView);
            name = (TextView) view.findViewById(R.id.name);
            score = (TextView) view.findViewById(R.id.score);
            level = (TextView) view.findViewById(R.id.level);
            date = (TextView) view.findViewById(R.id.date);
        }
    }


    public HighscoreAdapter(List<Highscore> highscoreList) {
        this.highscoreList = highscoreList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.highscore_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Highscore highscore = highscoreList.get(position);
        holder.name.setText(highscore.getName());
        holder.score.setText(highscore.getScore());
        holder.level.setText(highscore.getLevel());
        holder.date.setText(highscore.getDate());

        switch (position) {
            case 1: holder.image.setImageResource(R.drawable.first_place);
            break;
            case 2: holder.image.setImageResource(R.drawable.second_place);
            break;
            case 3: holder.image.setImageResource(R.drawable.third_place);
            break;
            case 0: holder.name.setTypeface(null, Typeface.BOLD);
                    holder.score.setTypeface(null, Typeface.BOLD);
                    holder.score.setMinWidth(100);
                    holder.score.setMaxWidth(100);
                    holder.level.setTypeface(null, Typeface.BOLD);
                    holder.level.setMinWidth(200);
                    holder.level.setMaxWidth(200);
                    holder.date.setTypeface(null, Typeface.BOLD);
            default: holder.image.setImageResource(R.drawable.clear);
        }
    }

    @Override
    public int getItemCount() {
        return highscoreList.size();
    }

    public void setHighscoreList(List<Highscore> highscoreList) {
        this.highscoreList = highscoreList;
    }
}
