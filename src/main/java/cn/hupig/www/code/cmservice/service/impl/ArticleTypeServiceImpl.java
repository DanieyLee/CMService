package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.ArticleTypeService;
import cn.hupig.www.code.cmservice.domain.ArticleType;
import cn.hupig.www.code.cmservice.repository.ArticleTypeRepository;
import cn.hupig.www.code.cmservice.service.dto.ArticleTypeDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArticleType}.
 */
@Service
@Transactional
public class ArticleTypeServiceImpl implements ArticleTypeService {

    private final Logger log = LoggerFactory.getLogger(ArticleTypeServiceImpl.class);

    private final ArticleTypeRepository articleTypeRepository;

    private final ArticleTypeMapper articleTypeMapper;

    public ArticleTypeServiceImpl(ArticleTypeRepository articleTypeRepository, ArticleTypeMapper articleTypeMapper) {
        this.articleTypeRepository = articleTypeRepository;
        this.articleTypeMapper = articleTypeMapper;
    }

    @Override
    public ArticleTypeDTO save(ArticleTypeDTO articleTypeDTO) {
        log.debug("Request to save ArticleType : {}", articleTypeDTO);
        ArticleType articleType = articleTypeMapper.toEntity(articleTypeDTO);
        articleType = articleTypeRepository.save(articleType);
        return articleTypeMapper.toDto(articleType);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArticleTypes");
        return articleTypeRepository.findAll(pageable)
            .map(articleTypeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleTypeDTO> findOne(Long id) {
        log.debug("Request to get ArticleType : {}", id);
        return articleTypeRepository.findById(id)
            .map(articleTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArticleType : {}", id);
        articleTypeRepository.deleteById(id);
    }
}
