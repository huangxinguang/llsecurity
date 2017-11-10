package com.laile.security.core.dao.file;

import com.laile.security.core.dao.IBaseDAO;
import com.laile.security.core.model.file.File;
import org.springframework.stereotype.Repository;


@Repository
public interface FileDAO extends IBaseDAO<File,String> {
}