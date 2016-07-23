package kz.islamgylymhaly;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by Эльдар on 18.07.2015.
 */
public class PageFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    String str, theme;

    int pageNumber;
    int backColor;

    static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page, null);
        readFile(pageNumber);

        TextView tvPage = (TextView) view.findViewById(R.id.theme);
        tvPage.setText(theme);

        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(Html.fromHtml(str));

        return view;
    }

    public void readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
        }
        int ok = 0;
        String string = outputStream.toString();
        str = theme = "";
        for (int i=0; i<string.length(); ++i) {
            if (ok == 0 && string.charAt(i) == '\n') ok = 1; else
            if (ok > 0) {
                if (string.charAt(i) == '\n') str += "<br>";
                else str += string.charAt(i);
            }
            else {
                if (string.charAt(i) == '/') theme += "\n";
                else theme += string.charAt(i);
            }
        }
    }

    public void readFile(int pos) {
        int id = 0;
        try {
            id = MainActivity.fields[pos].getInt(MainActivity.fields[pos]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        InputStream is = getResources().openRawResource(id);
        readTextFile(is);
    }
}
