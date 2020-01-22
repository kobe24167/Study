from pdf2image import convert_from_path, convert_from_bytes
from pdf2image.exceptions import (
    PDFInfoNotInstalledError,
    PDFPageCountError,
    PDFSyntaxError
)

convert_from_path('C:/Users/wbliang/Documents/GitHub/Study/Python/example/img/Python100.pdf', 300, "C:/Users/wbliang/Documents/GitHub/Study/Python/example/img/output", fmt="JPEG", output_file="essay", thread_count=1)
