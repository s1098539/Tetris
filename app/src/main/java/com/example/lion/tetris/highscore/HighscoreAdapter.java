package com.example.lion.tetris.highscore;

/**
 * Created by lion on 16/03/2018.
 */
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.example.lion.tetris.R;

        import java.util.List;

public class HighscoreAdapter extends RecyclerView.Adapter<HighscoreAdapter.MyViewHolder> {

    private List<Highscore> highscoreList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, score, level, date;

        public MyViewHolder(View view) {
            super(view);
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
        Highscore Highscore = highscoreList.get(position);
        holder.name.setText(Highscore.getName());
        holder.score.setText(Highscore.getScore());
        holder.level.setText(Highscore.getLevel());
        holder.date.setText(Highscore.getDate());
    }

    @Override
    public int getItemCount() {
        return highscoreList.size();
    }

    public void setHighscoreList(List<Highscore> highscoreList) {
        this.highscoreList = highscoreList;
    }
}
