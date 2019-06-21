package com.learning.cloud.ding.picture.dao;

import com.learning.cloud.ding.picture.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yyt
 * @Date: 2019-03-22 15:15
 * @Desc:
 */
@Repository
@Mapper
public interface PictureDao {
    int addPic(Picture picture);

    Picture getPic(Long picId);
}
