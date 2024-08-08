package cn.iocoder.yudao.module.ai.service.knowledge;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AI 知识库 Service 实现类
 *
 * @author xiaoxin
 */
@Service
@Slf4j
public class DocServiceImpl implements DocService {

    @Resource
    RedisVectorStore vectorStore;
    @Resource
    TokenTextSplitter tokenTextSplitter;

    // TODO @xin 临时测试用，后续删
    @Value("classpath:/webapp/test/Fel.pdf")
    private org.springframework.core.io.Resource data;


    @Override
    public void embeddingDoc() {
        // 读取文件
        org.springframework.core.io.Resource file = data;
        TikaDocumentReader loader = new TikaDocumentReader(file);
        List<Document> documents = loader.get();
        // 文档分段
        List<Document> segments = tokenTextSplitter.apply(documents);
        // 向量化并存储
        vectorStore.add(segments);
    }
}
