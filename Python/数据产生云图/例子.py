import os

from os import path
from wordcloud import WordCloud
import jieba

# get data directory (using getcwd() is needed to support running example in generated IPython notebook)
d = path.dirname(__file__) if "__file__" in locals() else os.getcwd()

# Read the whole text.
text = open(path.join(d, 'bug3.txt')).read()

text_list = jieba.cut(text, cut_all=True)
text=' '.join(text_list)

# Generate a word cloud image
wordcloud = WordCloud().generate(text)

# Display the generated image:
# the matplotlib way:
import matplotlib.pyplot as plt
plt.imshow(wordcloud, interpolation='bilinear')
plt.axis("off")

# lower max_font_size
font = r'C:\\Windows\\Fonts\\simkai.ttf'
wordcloud = WordCloud(max_font_size=40, font_path=font).generate(text)
plt.figure()
plt.imshow(wordcloud, interpolation="bilinear")
plt.axis("off")
plt.show()