
package com.example.pjhappybank.Fragment;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.fragment.app.Fragment;

        import com.example.pjhappybank.R;

public class CongratulationFragment extends Fragment {

    private TextView textViewQuantity;

    public CongratulationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_congratulation, container, false);

        textViewQuantity = view.findViewById(R.id.add_heart_fragment_congratulation);

        if (getArguments() != null) {
            int quantity = getArguments().getInt("quantity", 0);
            updateQuantity(quantity);
        }
        ImageView nextButton = view.findViewById(R.id.next_fragment_congratulation);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToNegativeFragment();
            }
        });

        return view;
    }

    private void updateQuantity(int quantity) {
        textViewQuantity.setText(String.valueOf(quantity));
    }
    private void switchToNegativeFragment() {
        NegativeFragment negativeFragment = new NegativeFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, negativeFragment)
                .commit();
    }
}
