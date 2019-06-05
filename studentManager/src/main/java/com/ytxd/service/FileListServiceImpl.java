package com.ytxd.service;

import com.ytxd.common.Response;
import com.ytxd.common.SplitPageUtil;
import com.ytxd.dao.FileListMapper;
import com.ytxd.pojo.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class FileListServiceImpl implements FileListService {
    @Autowired
    private FileListMapper fileListMapper;


    /**
     * 根据目录查找
     *
     * @param categoryId
     * @return
     */
    @Override
    public Response selectCategotyId(Integer categoryId, Integer page, Integer rows) {
        if (categoryId == null) {
            throw new RuntimeException("所属类目不能为空");
        }
        List<FileList> fileLists = fileListMapper.selectCategotyId(categoryId);
        Response response = new Response();
        response.setResultList(fileLists);
        //分页
        if (Objects.isNull(page) || page == 0) {
            page = 1;
        }
        if (Objects.isNull(rows) || rows == 0) {
            rows = 15;
        }

        Integer totalPage = SplitPageUtil.getTotalPage(rows, fileLists);
        response.setTotelPage(totalPage);
        response.setTotalSize(fileLists.size());
        response.setResultList(SplitPageUtil.getPageContent(rows, page, fileLists));

        return response;
    }

    /**
     * 添加
     *
     * @param fileList
     * @return
     */
    @Override
    public int insert(FileList fileList) {
        Date date = new Date();
        java.sql.Date data1 = new java.sql.Date(date.getTime());
        if (!fileList.getFileId().isEmpty()) {
            FileList fileList1 = fileListMapper.selectFileId(fileList.getFileId());
            Date createTime = fileList1.getCreateTime();
            fileList.setCreateTime(createTime);
            fileList.setUpdateTime(data1);
            fileListMapper.deleteByPrimaryKey(fileList.getFileId());
        } else {
            fileList.setCreateTime(data1);

        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        fileList.setFileId(uuid);
        if (fileList.getCategoryId() == null) {
            throw new RuntimeException("请选择您要添加图片所属的类目");
        }

        return fileListMapper.insert(fileList);
    }


    /**
     * 根据id删除
     *
     * @param fileId
     * @return
     */

    @Override
    public int deleteByPrimaryKey(String fileId) {
        if (fileId == null) {
            throw new RuntimeException("fileId不能为空");
        }
        /*String realPath = session.getServletContext().getRealPath("upload");
        File file = new File("realPath");
        if(!file.exists()){
            throw new RuntimeException("您删除的文件名不存在");
        }
        file.delete();*/
        return fileListMapper.deleteByPrimaryKey(fileId);

    }


}
