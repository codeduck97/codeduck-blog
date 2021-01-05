package com.duck.code.file.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.commons.entity.file.Contract;
import com.duck.code.file.mapper.ContractMapper;
import com.duck.code.file.service.ContractService;
import com.duck.code.file.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2021-01-05
 */
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {

}
