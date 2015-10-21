package com.bigfish.v2exme.ui;

import Models.V2exBaseModel;
import Models.V2exHotNewsModel;
import Models.V2exNodeListModel;

/**
 * Created by shirley on 15/10/20.
 */
public interface IFragmentTapListener {

    public void tapFragmentListUserName(V2exBaseModel model);

    public void refreshHotNews();
    public void refreshFastNews();
    public void refreshNodeNews();

    public void tapHotNewsItem(V2exBaseModel model);
    public void tapFastNewsItem(V2exBaseModel model);
    public void tapNodeNewsItem(V2exNodeListModel model);

}
