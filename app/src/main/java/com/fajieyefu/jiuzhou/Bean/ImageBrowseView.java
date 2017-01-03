package com.fajieyefu.jiuzhou.Bean;

import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by Jelly on 2016/9/3.
 */
public interface ImageBrowseView {

    Intent getDataIntent();

    void setImageBrowse(List<FileInfo> images, int position);

    Context getMyContext();
}
