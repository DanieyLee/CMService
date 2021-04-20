package cn.hupig.www.code.cmservice.service.impl;

import cn.hupig.www.code.cmservice.service.ArticleEnclosureService;
import cn.hupig.www.code.cmservice.domain.ArticleEnclosure;
import cn.hupig.www.code.cmservice.repository.ArticleEnclosureRepository;
import cn.hupig.www.code.cmservice.service.dto.ArticleEnclosureDTO;
import cn.hupig.www.code.cmservice.service.mapper.ArticleEnclosureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link articleEnclosure}.
 */
@Service
@Transactional
public class ArticleEnclosureServiceImpl implements ArticleEnclosureService {

    private final Logger log = LoggerFactory.getLogger(ArticleEnclosureServiceImpl.class);

    private final ArticleEnclosureRepository articleEnclosureRepository;

    private final ArticleEnclosureMapper articleEnclosureMapper;

    public ArticleEnclosureServiceImpl(ArticleEnclosureRepository articleEnclosureRepository, ArticleEnclosureMapper articleEnclosureMapper) {
        this.articleEnclosureRepository = articleEnclosureRepository;
        this.articleEnclosureMapper = articleEnclosureMapper;
    }

    @Override
    public ArticleEnclosureDTO save(ArticleEnclosureDTO articleEnclosureDTO) {
        log.debug("Request to save articleEnclosure : {}", articleEnclosureDTO);
        ArticleEnclosure articleEnclosure = articleEnclosureMapper.toEntity(articleEnclosureDTO);
        articleEnclosure = articleEnclosureRepository.save(articleEnclosure);
        return articleEnclosureMapper.toDto(articleEnclosure);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleEnclosureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all articleEnclosures");
        return articleEnclosureRepository.findAll(pageable)
            .map(articleEnclosureMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ArticleEnclosureDTO> findOne(Long id) {
        log.debug("Request to get articleEnclosure : {}", id);
        return articleEnclosureRepository.findById(id)
            .map(articleEnclosureMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete articleEnclosure : {}", id);
        articleEnclosureRepository.deleteById(id);
    }
}
