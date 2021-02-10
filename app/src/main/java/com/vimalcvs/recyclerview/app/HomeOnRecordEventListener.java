package com.vimalcvs.recyclerview.app;

import com.vimalcvs.recyclerview.models.HomeResult;

public interface HomeOnRecordEventListener {

    void accept(HomeResult homeResult);

    void decline(HomeResult homeResult);
}
