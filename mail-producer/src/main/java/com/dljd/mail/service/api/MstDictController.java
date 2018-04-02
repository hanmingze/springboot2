package com.dljd.mail.service.api;

import com.dljd.mail.entity.MstDict;
import com.dljd.mail.service.MstDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jiaozhiguang on 2018/3/10.
 */
@RestController
public class MstDictController {

    @Autowired
    private MstDictService mstDictService;

//    @ReadOnlyAnnotation
    @GetMapping("/findByStatus")
    public List<MstDict> findByStatus(String status) {
        return mstDictService.findByStatus(status);
    }

}
