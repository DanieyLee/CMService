package cn.hupig.www.code.cmservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hupig.www.code.cmservice.repository.ArticleEnclosureRepository;
import cn.hupig.www.code.cmservice.service.Rewrite_ArticleEnclosureService;
import cn.hupig.www.code.cmservice.service.dto.ArticleEnclosureDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleEnclosureMapper;
import cn.hupig.www.code.cmservice.web.rest.vm.FileAndNameVM;

/**
 * Service Implementation for managing {@link articleEnclosure}.
 */
@Service
@Transactional
public class Rewrite_ArticleEnclosureServiceImpl implements Rewrite_ArticleEnclosureService {

    private final Logger log = LoggerFactory.getLogger(Rewrite_ArticleEnclosureServiceImpl.class);

    private final ArticleEnclosureRepository articleEnclosureRepository;

    private final ArticleEnclosureMapper articleEnclosureMapper;

    public Rewrite_ArticleEnclosureServiceImpl(ArticleEnclosureRepository articleEnclosureRepository, ArticleEnclosureMapper articleEnclosureMapper) {
        this.articleEnclosureRepository = articleEnclosureRepository;
        this.articleEnclosureMapper = articleEnclosureMapper;
    }

	@Override
	public ArticleEnclosureDTO createArticleEnclosure(FileAndNameVM fileAndNameVM) {
//	        try {
//	            FileInputStream fis = new FileInputStream(file);
//	            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
//	            byte[] b = new byte[1000];
//	            int n;
//	            while ((n = fis.read(b)) != -1) {
//	                bos.write(b, 0, n);
//	            }
//	            fis.close();
//	            byte[] data = bos.toByteArray();
//	            bos.close();
//	            return FileOperation.save(data, file.getName(), "article");
//	        } catch (Exception e) {
//	            return "上传报错";
//	        }
		return null;
	}
}
