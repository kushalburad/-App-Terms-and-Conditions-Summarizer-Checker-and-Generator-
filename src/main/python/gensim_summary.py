from gensim.summarization.summarizer import summarize
from gensim.summarization import keywords
def main(input):
    mystr=input
    op=""
    summ_words = summarize(mystr,word_count=200)
    l = []
    for i in range(len(summ_words)):
        l.append(summ_words[i])
    l2 = l
    seperator=""
    final_str = seperator.join(l)
    final_str1 = final_str.split(".")
    for i in range(len(final_str1)):
        final_str1[i]=final_str1[i]+'.'
        op = op+final_str1[i]
    return ""+op