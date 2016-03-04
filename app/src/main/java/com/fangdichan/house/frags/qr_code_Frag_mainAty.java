package com.fangdichan.house.frags;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangdichan.house.R;
import com.fangdichan.house.utils.StaticValues;
import com.fangdichan.house.zxing.atys.ScanQrCodeActivity;
import com.fangdichan.house.zxing.encoding.EncodingHandler;
import com.google.zxing.WriterException;

import java.util.Formatter;
import java.util.Locale;


/**
 * Created by wy on 2015/9/14.
 */
public class qr_code_Frag_mainAty extends Fragment {

    TextView tv_num,tv_id;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    char[] box={'3','8','4','0','6','2','9','1','7','5'};

    public String format(String input)
    {
        String output="";
        char[] array=input.toCharArray();
        for(int x=0;x<array.length;x++)
        {
            if(((int)(array[x]-'0'))>=0&&((int)(array[x]-'0'))<10)
                output+=box[((int)(array[x]-'0'))];
            else
                return "";
        }
        return output;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static qr_code_Frag_mainAty newInstance(String param1, String param2) {
        qr_code_Frag_mainAty fragment = new qr_code_Frag_mainAty();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public qr_code_Frag_mainAty() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code_mainaty, container, false);
        tv_num=(TextView) view.findViewById(R.id.tv_qr_code_num);
        tv_id=(TextView) view.findViewById(R.id.tv_qr_code_id);
        int num=0;
        if(StaticValues.user_info!=null)
        num=10-StaticValues.user_info.getChildSum();
        SpannableString ss = new SpannableString("* 您的发展权利还剩"+num+"名");
        if(num<10) {
            ss.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.text_color)), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.orange)), 10, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.text_color)), 11, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else
        {
            ss.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.text_color)), 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.orange)), 10, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.text_color)), 12, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv_num.setText(ss);
        ImageView iv_mar = (ImageView) view.findViewById(R.id.iv_edit_mar);
        Bitmap qrCodeBitmap;
        String contentString="null";
        if(StaticValues.user_info!=null) {
            contentString = "http://211.87.239.112:8080/FangDiChan/regist/house_regist.html?id=" + StaticValues.user_info.getId();
            String id = format(StaticValues.user_info.getId());
            tv_id.setText("ID:"+id);
        }
        try {
            qrCodeBitmap = EncodingHandler.createQRCode(contentString, 200);
            iv_mar.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return view;
    }

}
