package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.EmojiViewModel;

public class EmojiFragment extends Fragment {

    private EmojiViewModel emojiViewModel;
    private String selectedEmoji = "";

    public EmojiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emoji, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emojiViewModel = new EmojiViewModel();

        ImageView happyEmoji = view.findViewById(R.id.happyEmoji_fragmentEmoji);
        ImageView excitedEmoji = view.findViewById(R.id.excitedEmoji_fragmentEmoji);
        ImageView normalEmoji = view.findViewById(R.id.normalEmoji_fragmentEmoji);
        ImageView sadEmoji = view.findViewById(R.id.sadEmoji_fragmentEmoji);
        ImageView angryEmoji = view.findViewById(R.id.angryEmoji_fragmentEmoji);

        // Set click listeners for emoji images
        happyEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEmojiSelection("happy");
            }
        });

        excitedEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEmojiSelection("excited");
            }
        });

        normalEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEmojiSelection("normal");
            }
        });

        sadEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEmojiSelection("sad");
            }
        });

        angryEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEmojiSelection("angry");
            }
        });

        ImageView arrowImageView = view.findViewById(R.id.arrow_right_fragmentEmoji);
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelectedEmojiToFirestore();
                switchToHomeFragment();
            }
        });
    }

    private void handleEmojiSelection(String emoji) {
        selectedEmoji = emoji;
    }

    private void saveSelectedEmojiToFirestore() {
        if (!selectedEmoji.isEmpty()) {
            emojiViewModel.saveEmojiToFirestore(selectedEmoji, new EmojiViewModel.OnEmojiSaveListener() {
                @Override
                public void onEmojiSaveSuccess() {
                    // Handle success
                }

                @Override
                public void onEmojiSaveFailure(String errorMessage) {
                    // Handle failure
                }
            });
        } else {
            // Handle case when selectedEmoji is empty
        }
    }

    private void switchToHomeFragment() {
        HomeFragment homeFragment = new HomeFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit();
    }
}
