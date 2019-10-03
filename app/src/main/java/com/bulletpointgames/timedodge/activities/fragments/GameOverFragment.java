package com.bulletpointgames.timedodge.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bulletpointgames.timedodge.R;

public class GameOverFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POINTS = "points";
    private static final String ARG_BONUSES = "bonuses";

    // TODO: Rename and change types of parameters
    private int points = 0;
    private int bonuses = 0;

    private GameOverFragmentInteractionListener mListener;

    public GameOverFragment()
    {
        // Required empty public constructor
    }

    public static GameOverFragment newInstance(int points, int bonuses)
    {
        GameOverFragment fragment = new GameOverFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POINTS, points);
        args.putInt(ARG_BONUSES, bonuses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            points = getArguments().getInt(ARG_POINTS);
            bonuses = getArguments().getInt(ARG_BONUSES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gameover, container, false);
        view.findViewById(R.id.frag_gameover_btn_menu).setOnClickListener(v -> {
            if (validateListenerExists())
                mListener.onGameOverMenuButtonPressed();
        });
        view.findViewById(R.id.frag_gameover_btn_replay).setOnClickListener(v -> {
            if (validateListenerExists())
                mListener.onGameOverReplayButtonPressed();
        });
        return view;
    }

    public boolean validateListenerExists()
    {
        return (mListener != null);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof GameOverFragmentInteractionListener)
        {
            mListener = (GameOverFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface GameOverFragmentInteractionListener
    {
        void onGameOverMenuButtonPressed();
        void onGameOverReplayButtonPressed();
    }
}
