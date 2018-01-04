package com.augur.zongyang.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.augur.zongyang.R;
import com.augur.zongyang.bean.CurrentUser;
import com.augur.zongyang.model.CustomTreeForm;
import com.augur.zongyang.network.helper.NetworkHelper;
import com.augur.zongyang.network.operator.MyWorkHttpOpera;
import com.augur.zongyang.tree.bean.Node;
import com.augur.zongyang.tree.bean.SimpleTreeAdapter;
import com.augur.zongyang.tree.bean.TreeFileBean;
import com.augur.zongyang.tree.bean.TreeListViewAdapter;
import com.augur.zongyang.util.StringUtil;
import com.augur.zongyang.util.asynctask.GetDataFromNetAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 *附件目录
 */
public class AttachmentCatalogFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Activity activity;
    private View fragmentView;

    private ListView mTree;
    private List<TreeFileBean> mDatas;
    private TreeListViewAdapter mAdapter;
    private List<CustomTreeForm> forms;

    private OnFragmentInteractionListener mListener;

    public AttachmentCatalogFragment() {
        // Required empty public constructor
    }

    public static AttachmentCatalogFragment newInstance(String param1, String param2) {
        AttachmentCatalogFragment fragment = new AttachmentCatalogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_attachment_catalog,container, false);
        mTree = fragmentView.findViewById(R.id.listView);

        return fragmentView;
    }

    private void searchData() {
        new GetDataFromNetAsyncTask<List<CustomTreeForm>, String>(activity,
                "",
                new GetDataFromNetAsyncTask.GetDataFromNetAsyncTaskListener<List<CustomTreeForm>, String>() {
                    @Override
                    public List<CustomTreeForm> getResult(
                            String... strings) {
                        Long userId = -1L;
                        if (CurrentUser.getInstance().getCurrentUser() != null) {
                            userId = CurrentUser.getInstance().getCurrentUser()
                                    .getUserId();
                        }
                        return NetworkHelper
                                .getInstance(activity)
                                .getHttpOpera(
                                        MyWorkHttpOpera.class).getAttachmentCatalog();
                    }

                    @Override
                    public void onSuccess(List<CustomTreeForm> resultData) {
                        if (resultData != null && resultData.size() > 0) {
//							Log.e(TAG, "datas.size:" + resultData.size());
                            forms = resultData;
                            initData();
                        }
                    }

                    @Override
                    public void onFail() {
                    }
                }).execute();
    }

    private void initData(){
        mDatas = new ArrayList<>();
        try {
            setData(forms);
            mAdapter = new SimpleTreeAdapter<TreeFileBean>(mTree,activity,
                    mDatas, 0);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if(node.isLeaf()){
                        String proId = node.getProjectId();
                        String code = node.getId();

                        Intent intent = new Intent();
                        intent.setClass(activity, null);
                        Bundle bundle = new Bundle();
                        bundle.putString("projectId", proId);
                        bundle.putString("code", code);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void setData(List<CustomTreeForm> forms) {
        if(forms == null)
            return;

        for(CustomTreeForm form : forms){
            String _id = StringUtil.getNotNullString(form.getCode(), "");
            String name = StringUtil.getNotNullString(form.getText(), "");
            String proId = StringUtil.getNotNullString(form.getProjectId(), "");
            String parentId = StringUtil.getNotNullString(form.getPcode(), "");
            String itemId = StringUtil.getNotNullString(form.getId(),"");
            mDatas.add(new TreeFileBean(_id, parentId, name, proId,itemId));
            if(form.getChildren()!=null)
                setData(form.getChildren());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
