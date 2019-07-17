import json
from datetime import datetime
from App.__init__ import db

class ConversionRecord(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    path = db.Column(db.String(255))
    user = db.Column(db.String(512))
    state = db.Column(db.String(255))
    file_type = db.Column(db.String(255))
    result = db.Column(db.String(255))
    ctime = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
    etime = db.Column(db.DateTime)
    value  = db.Column(db.Text)

    def __repr__(self):
        return '<ConversionRecord %r>' % self.path

class ConversionEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, ConversionRecord):
            return {'path':obj.path, 'user':obj.user, 'ctime':obj.ctime.strftime('%Y-%m-%d %H:%M:%S') , 'type':obj.file_type, 'value':obj.value}
        # Let the base class default method raise the TypeError
        return json.JSONEncoder.default(self, obj)
