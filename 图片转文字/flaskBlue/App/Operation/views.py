from App.Operation import operation
from PIL import Image
import pytesseract
from App.ConversionRecord.models import *

@operation.route('/')  # 设置路由
def index():  # 执行的方法
    return 'This Page Is Index'

@operation.route('/transformAll')
def transformAll():
    result = ConversionRecord.query.filter_by(state=0).all()
    for item in result:
        value = transform(item.path)
        item.value = value
        item.state = 1
        db.session.commit()
    return 'ok'

def transform(path):
    # pytesseract.pytesseract.tesseract_cmd = r'D:\\Program Files\\Tesseract-OCR\\tesseract.exe'
    result = pytesseract.image_to_string(Image.open(path))
    return result