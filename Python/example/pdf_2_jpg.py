from pdf2jpg import pdf2jpg
inputpath = r"C:/Users/wbliang/Documents/GitHub/Study/Python/example/img/1.pdf"
outputpath = r"C:/Users/wbliang/Documents/GitHub/Study/Python/example/img/output"
# To convert single page
# result = pdf2jpg.convert_pdf2jpg(inputpath, outputpath, dpi=300, pages="1")
# print(result)

# # To convert multiple pages
# result = pdf2jpg.convert_pdf2jpg(inputpath, outputpath, dpi=300, pages="1,0,3")
# print(result)

# to convert all pages
result = pdf2jpg.convert_pdf2jpg(inputpath, outputpath, dpi=200, pages="ALL")
print(result)