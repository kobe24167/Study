import { Upload, Icon, message, Button, Table, Divider, Tag } from 'antd';
import axios from 'axios';
import {CopyToClipboard} from 'react-copy-to-clipboard';

function getBase64(img, callback) {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result));
  reader.readAsDataURL(img);
}

function beforeUpload(file) {
  const isJPG = file.type === 'image/jpeg';
  const isPNG = file.type === 'image/png';
  if (!isJPG && !isPNG) {
    message.error('You can only upload JPG file!');
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error('Image must smaller than 2MB!');
  }
  return isJPG && isLt2M;
}

function valueSubStr(value) {
  
  if (value != null && value.length > 10){
    value = value.substr(0,20)+'...'
  }
  console.log(value)
  return value
}

class Avatar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {data: 0, loading:true};
  }

  handleChange = info => {
    if (info.file.status === 'uploading') {
      this.setState({ loading: true });
      return;
    }
    if (info.file.status === 'done') {
      // Get this url from response in real world.
      getBase64(info.file.originFileObj, imageUrl =>
        this.setState({
          imageUrl,
          loading: false,
        }),
      );
    }
  };

  componentDidMount() {
    let _this = this
    axios.get('http://114.67.79.59:8080/conversionRecord/query')
    // axios.get('http://localhost:8080/conversionRecord/query')
      .then(function (response) {
        console.log(response.data);
        _this.setState({data: response.data})
      })
      .catch(function (error) {
        console.log(error);
      })
      .then(function () {
        // always executed
      });
    
    console.log('componentDidMount')
  }

  transformAll = () => {
    axios.get('http://114.67.79.59:8080/operation/transformAll')
    // axios.get('http://localhost:8080/operation/transformAll')
    .then(function (response) {
      console.log(response.data);
    })
    .catch(function (error) {
      console.log(error);
    })
    .then(function () {
      // always executed
    });
  };
  
  render() {
    const uploadButton = (
      <div>
        <Icon type={this.state.loading ? 'loading' : 'plus'} />
        <div className="ant-upload-text">Upload</div>
      </div>
    );

    const uploadProps = {
      action: 'http://114.67.79.59:8080/uploadFile/upload',
      // action: 'http://localhost:8080/uploadFile/upload',
      multiple: false,
      data: { a: 1, b: 2 },
      headers: {
        Authorization: '$prefix $token',
        'Access-Control-Allow-Origin':'*'
      },
      onStart(file) {
        console.log('onStart', file, file.name);
      },
      onSuccess(ret, file) {
        console.log('onSuccess', ret, file.name);
      },
      onError(err) {
        console.log('onError', err);
      },
      onProgress({ percent }, file) {
        console.log('onProgress', `${percent}%`, file.name);
      },
      customRequest({
        action,
        data,
        file,
        filename,
        headers,
        onError,
        onProgress,
        onSuccess,
        withCredentials,
      }) {
        // EXAMPLE: post form-data with 'axios'
        const formData = new FormData();
        if (data) {
          Object.keys(data).map(key => {
            formData.append(key, data[key]);
          });
        }
        formData.append(filename, file);
    
        axios
          .post(action, formData, {
            withCredentials,
            headers,
            onUploadProgress: ({ total, loaded }) => {
              onProgress({ percent: Math.round(loaded / total * 100).toFixed(2) }, file);
            },
          })
          .then(({ data: response }) => {
            onSuccess(response, file);
          })
          .catch(onError);
    
        return {
          abort() {
            console.log('upload progress is aborted.');
          },
        };
      },
    };
    
    const { imageUrl } = this.state;
    const columns = [
      {
        title: 'Path',
        dataIndex: 'path',
        key: 'path',
        render: text => <a href="javascript:;">{text}</a>,
      },
      {
        title: 'Type',
        dataIndex: 'type',
        key: 'type',
      },
      {
        title: 'Upload time',
        dataIndex: 'ctime',
        key: 'ctime',
      },
      {
        title: 'User',
        key: 'user',
        dataIndex: 'user',
      },
      {
        title: 'Value',
        key: 'value',
        dataIndex: 'value',
        render: (text, record) => valueSubStr(text)
      },
      {
        title: 'Operation',
        key: 'value',
        render: (text, record) => (
          <span>
            <a href="javascript:;">Transform {record.name}</a>
            <Divider type="vertical" />
            <CopyToClipboard text={record.value}>
              <Button>Copy</Button>
            </CopyToClipboard>
          </span>
        ),
      },
    ];
    return (
      <div>
        <Upload {...uploadProps}>
          <Button>
            <Icon type="upload" /> Click to Upload
          </Button>
        </Upload>
        <Table columns={columns} dataSource={this.state.data} />
        <Button onClick={this.transformAll}>
          <Icon type="caret-right" /> Transform All
        </Button>
      </div>
    );
  }
}

export default Avatar;