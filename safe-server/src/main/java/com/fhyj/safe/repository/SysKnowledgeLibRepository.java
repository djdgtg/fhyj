package com.fhyj.safe.repository;

import com.fhyj.safe.entity.SysKnowledgeLib;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/2/21 15:32
 */
public interface SysKnowledgeLibRepository extends ElasticsearchRepository<SysKnowledgeLib, Long> {

    @Highlight(
            fields = {
                    @HighlightField(name = "title"),
                    @HighlightField(name = "keyWords")
            },
            parameters = @HighlightParameters(preTags = "<span style='color:red'>", postTags = "</span>")
    )
    @Query("{\n" +
            "  \"multi_match\": {\n" +
            "    \"query\": \"?0\",\n" +
            "    \"fields\": [\n" +
            "      \"title\",\n" +
            "      \"keyWords\"\n" +
            "    ]\n" +
            "  }\n" +
            "}")
    SearchPage<SysKnowledgeLib> search(String keyword, Pageable pageable);
}
