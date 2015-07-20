package ee.esutoniagodesu.service;

import com.google.common.base.Joiner;
import ee.esutoniagodesu.bean.ProjectDAO;
import ee.esutoniagodesu.domain.jmen.view.EN_Essum;
import ee.esutoniagodesu.repository.project.JMDictEnDB;
import ee.esutoniagodesu.util.lang.lingv.JapaneseCharacter;
import ee.esutoniagodesu.web.rest.dto.VocabularyDTO;
import org.apache.log4j.Logger;
import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class KuromojiService {

    private static final Logger log = Logger.getLogger(KuromojiService.class);
    private static Tokenizer _tokenizer;

    @Inject
    private ProjectDAO dao;

    @Inject
    private JMDictEnDB jmDictEnDB;

    private Tokenizer tokenizer() {
        if (_tokenizer == null) {
            _tokenizer = Tokenizer.builder().build();
        }
        return _tokenizer;
    }

    public List<VocabularyDTO> asDTO(String sentence) {
        return asDTO(tokenize(sentence));
    }

    private List<Token> tokenize(String sentence) {
        List<Token> result = tokenizer().tokenize(sentence);
        log.debug("tokenize: result.size=" + result.size() + ", sentence=" + sentence);
        return result;
    }

    private List<VocabularyDTO> asDTO(List<Token> tokens) {
        List<VocabularyDTO> result = new ArrayList<>();

        for (Token p : tokens) {
            //ei töötle lühikesi hiraganas lõike
            if (p.getBaseForm() != null && (JapaneseCharacter.isKanji(p.getBaseForm().charAt(0)) || p.getBaseForm().length() > 2)) {
                VocabularyDTO dto = new VocabularyDTO();
                dto.txt = p.getBaseForm();
                dto.rdng = toHiragana(p.getReading());
                List<EN_Essum> essums = jmDictEnDB.getEssumByKanjAndRdng(dto.txt, dto.rdng);
                List<String> glosses = essums.stream().map(EN_Essum::getGloss).collect(Collectors.toList());
                dto.gloss = Joiner.on("; ").join(glosses);
                result.add(dto);
            }
        }

        return result;
    }

    private static String toHiragana(String katakana) {
        StringBuilder result = new StringBuilder();

        for (char c : katakana.toCharArray()) {
            result.append(JapaneseCharacter.toHiragana(c));
        }

        return result.toString();

    }
}
