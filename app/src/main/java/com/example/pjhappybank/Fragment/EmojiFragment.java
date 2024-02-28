package com.example.pjhappybank.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pjhappybank.R;
import com.example.pjhappybank.ViewModel.EmojiViewModel;

public class EmojiFragment extends Fragment {

    private EmojiViewModel emojiViewModel;
    private String selectedEmoji = "";

    public EmojiFragment() {
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
                if (selectedEmoji.isEmpty()) {
                    showEmojiSelectionPrompt();
                } else {
                    saveSelectedEmojiToFirestore();
                    switchToMainFragment();
                }
            }
        });
    }

    private void handleEmojiSelection(String emoji) {
        selectedEmoji = emoji;

        // Reset background for all ImageViews
        resetBackgrounds();

        // Set background for the selected ImageView
        ImageView selectedImageView = getCorrespondingImageView(emoji);
        if (selectedImageView != null) {
            selectedImageView.setBackgroundResource(R.drawable.select_background);
        }
    }

    // Helper method to reset background for all ImageViews
    private void resetBackgrounds() {
        ImageView happyEmoji = getView().findViewById(R.id.happyEmoji_fragmentEmoji);
        ImageView excitedEmoji = getView().findViewById(R.id.excitedEmoji_fragmentEmoji);
        ImageView normalEmoji = getView().findViewById(R.id.normalEmoji_fragmentEmoji);
        ImageView sadEmoji = getView().findViewById(R.id.sadEmoji_fragmentEmoji);
        ImageView angryEmoji = getView().findViewById(R.id.angryEmoji_fragmentEmoji);

        happyEmoji.setBackgroundResource(0);
        excitedEmoji.setBackgroundResource(0);
        normalEmoji.setBackgroundResource(0);
        sadEmoji.setBackgroundResource(0);
        angryEmoji.setBackgroundResource(0);
    }

    // Helper method to get the corresponding ImageView for the emoji
    private ImageView getCorrespondingImageView(String emoji) {
        switch (emoji) {
            case "happy":
                return getView().findViewById(R.id.happyEmoji_fragmentEmoji);
            case "excited":
                return getView().findViewById(R.id.excitedEmoji_fragmentEmoji);
            case "normal":
                return getView().findViewById(R.id.normalEmoji_fragmentEmoji);
            case "sad":
                return getView().findViewById(R.id.sadEmoji_fragmentEmoji);
            case "angry":
                return getView().findViewById(R.id.angryEmoji_fragmentEmoji);
            default:
                return null;
        }
    }

    private void saveSelectedEmojiToFirestore() {
        if (!selectedEmoji.isEmpty()) {
            emojiViewModel.saveEmojiToFirestore(selectedEmoji, new EmojiViewModel.OnEmojiSaveListener() {
                @Override
                public void onEmojiSaveSuccess() {
                    // Handle successful save
                }

                @Override
                public void onEmojiSaveFailure(String errorMessage) {
                    // Handle save failure
                }
            });
        } else {
            // Handle case when selectedEmoji is empty
        }
    }


    private void switchToMainFragment() {
        MainFragment mainFragment = new MainFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mainFragment)
                .commit();
    }

    private void showEmojiSelectionPrompt() {
        Toast.makeText(requireContext(), "Mời chọn trạng thái của bạn", Toast.LENGTH_SHORT).show();
    }
}
