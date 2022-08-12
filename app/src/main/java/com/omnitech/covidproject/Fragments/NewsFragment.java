package com.omnitech.covidproject.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnitech.covidproject.Adapter.NewsAdapter;
import com.omnitech.covidproject.Interfaces.ApiCallBack;
import com.omnitech.covidproject.Model.Article;
import com.omnitech.covidproject.Model.News;
import com.omnitech.covidproject.R;
import com.omnitech.covidproject.Utils.Constants;
import com.omnitech.covidproject.Utils.CovidApi;
import com.omnitech.covidproject.Utils.ProgressDialogManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment  implements ApiCallBack {



    RecyclerView recyclerView;
    CovidApi covidApi;
    NewsAdapter newsAdapter;
    List<Article> list=new ArrayList<>();
    AlertDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view)
    {

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        covidApi=new CovidApi(getActivity(),this);
        newsAdapter=new NewsAdapter(list,getActivity());
        recyclerView.setAdapter(newsAdapter);
        progressDialog= ProgressDialogManager.getProgressDialog(getActivity());

        covidApi.getData(Constants.COVID_URL_NEWS,Constants.COVID_DATA_NEWS);
        progressDialog.show();





    }

    @Override
    public void onCovidResult(String response, int Code, int DataType)
    {

        progressDialog.dismiss();
        if(Code==Constants.SUCCESS_CODE)
        {

            list.clear();
            Type type = new TypeToken<News>() {}.getType();
            News news = new Gson().fromJson(response, type);
            list.addAll(news.getArticles());
            newsAdapter.notifyDataSetChanged();
        }


    }
}