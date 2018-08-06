# -*- encoding: utf-8 -*-

import sys
import os
import code
from nltk.tree import Tree
from stanford import *

reload(sys)
sys.setdefaultencoding('utf-8')

os.environ['JAVA_HOME'] = 'C:\\Program Files\\Java\\jdk1.8.0_131\\bin\\java.exe'

root = 'E:/nlp/nltk_data/stanford-corenlp/'
modelpath = root + 'models/lexparser/chinesePCFG.ser.gz'
opttype = 'typedDependencies' # penn typedDependencies

parser = StanfordParser(modelpath,root,opttype)
result = parser.parse('罗马尼亚 的 首都 是 布加勒斯特 。')

print code.coder(result)

#tree = Tree.fromstring(result)

#tree.draw()