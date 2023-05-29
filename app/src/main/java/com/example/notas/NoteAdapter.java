package com.example.notas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> noteList;

    public NoteAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.bottomMargin = 0;
        view.setLayoutParams(layoutParams);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitleTextView;
        TextView noteDateTimeTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView);
            noteDateTimeTextView = itemView.findViewById(R.id.noteDateTimeTextView);
        }

        public void bind(Note note) {
            noteTitleTextView.setText(note.getTitle());
            String dateTime = note.getDate() + " " + note.getTime();
            noteDateTimeTextView.setText(dateTime);

        }
    }
}