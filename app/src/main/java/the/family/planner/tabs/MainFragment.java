package the.family.planner.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import the.family.planner.R;

public class MainFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container, false);

        initialiseItems();
        setWidgets();
        return rootView;
    }

    private void setWidgets() {

    }

    private void initialiseItems() {
        String sTitle = getArguments().getString("title");
    }
}