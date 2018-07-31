#encoding=utf-8
import sys
import code
from pyltp import *

sent = '在 包含 问题 的 所有 解 的 解空间树 中'
words = sent.split(" ")
# 词性标注
postagger = Postagger()
#导入词性标注模型
postagger.load("E:\\ltp-data-v3.3.1\\pos.model")

postags = postagger.postag(words)

for word,postag in zip(words,postags):
	print code.coder(word)+"/"+postag,

