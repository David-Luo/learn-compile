package com.bean.compare.stub.diff;

import com.bean.annotation.AutoImplementService;
import com.bean.compare.annotation.DiffStrategy;
import com.bean.compare.runtime.ChangeChecker;
import com.bean.compare.runtime.custom.Skip;
import com.bean.compare.stub.bean.Teacher;

@AutoImplementService
public interface TeacherChangeChecker  extends ChangeChecker<Teacher> {

    @DiffStrategy(key = "code")
    void students() ;

    @DiffStrategy(Skip.class)
    void updateTime();
}
