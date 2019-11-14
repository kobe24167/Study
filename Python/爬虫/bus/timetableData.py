from sqlalchemy import Column, String, create_engine, Integer
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()
class Timetable(Base):
    # 表的名字:
    __tablename__ = 'timetable'
    # 表的结构:
    id = Column(Integer, primary_key=True , autoincrement=True)
    line_name = Column(String(255))
    station = Column(String(255))
    start = Column(String(255))
    arrive = Column(String(255))
    time = Column(String(255))
    price = Column(String(255))
    busType = Column(String(255))
    duration = Column(String(255))
    distance = Column(String(255))
    desc = Column(String(255))
    data = Column(String(2000))

engine = create_engine('mysql+mysqlconnector://root:Wincor2008@localhost:3306/bus', pool_size=200, max_overflow=50)
# 创建DBSession类型:
DBSession = sessionmaker(bind=engine)
session = DBSession()
# t = session.query(Timetable).order_by(Timetable.id).offset(0).first()
# data = t.data
# clean(data)

def clean(data):
    data = data.replace('<b>','').replace('</b>','').replace('<td>','=').replace('</td>','').replace('<tr>','#').replace('</tr>','').replace('<tbody>','').replace('</tbody>','').replace('\n','').replace('','')
    data = data[data.index("时间"):-1]
    aa = data.split("#")
    dict = {}
    for a in aa:
        a = a.strip()
        if a.index("=") == 0 :
            a = a[1:]
        dict.setdefault(a[:a.index('=')].strip(), a[a.index('=')+1:].strip())
    print(dict)
    return dict

all = session.query(Timetable).order_by(Timetable.id).offset(0).all()
for t in all:
    data = t.data
    print(t.id)
    kv = clean(data)
    t.time = kv.get("时间")
    t.price = kv.get("车费")
    t.busType = kv.get("车型")
    t.duration = kv.get("耗时")
    t.distance = kv.get("里程")
    t.desc = kv.get("备注")
    
    # 提交即保存到数据库:
session.commit()

