﻿java -mx300m -cp "E:\\nlp\\standfordtagger\\standford-postagger.jar; E:\\nlp\\standfordpostagger\\lib\\slf4j-api.jar;E:\\nlp\\standfordpostagger\\lib\\slf4j-simple.jar" edu.standford.nlp.tagger.maxent.MaxentTagger -model "E:\\nlp\\standfordpostagger\\models\\chinese-distsim.tagger" -textFile postest.txt > result.txt