package com.learning.cloud.supervision.service;

import com.learning.cloud.supervision.entity.Supervision;
import com.learning.cloud.user.user.entity.User;
import com.learning.domain.JsonResult;
import org.springframework.web.multipart.MultipartFile;

public interface SupervisionService {
    JsonResult addSupervision(MultipartFile file, Supervision supervision) throws Exception;

    JsonResult getSupervision(Supervision supervision);

    JsonResult getSupervisionById(long id);

    JsonResult removeSupervisionById(Long id);

    JsonResult deleteSupervisionById(Long id);

    JsonResult updateSupervision(MultipartFile file, Supervision supervision) throws Exception;

    JsonResult getAllValidSupervision(Supervision supervision);

    JsonResult updateTopping(Long supervisionId);

    JsonResult batchUpdateSupervision(String ids, Integer status);

    JsonResult getBureauSupervision(Supervision supervision);

    JsonResult getToppingSupervision(Integer bureauId);

    JsonResult setSupervisor(User user);

    JsonResult getSupervisorIdentity(User user);

    JsonResult unsetToppingInSupervision(Long id);

    JsonResult getPicsForSupervision(Supervision supervision);
}
