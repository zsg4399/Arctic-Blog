const formatDate = (time) => {
  const datetime = new Date(time);
  const year = datetime.getFullYear();
  const month = datetime.getMonth() + 1; //getmonth获取到的月份从0开始，即0代表一月份
  const day = datetime.getDate(); //getdate获取到的是一个月中的某一天，getday获取的是星期几
  return (
    year +
    "." +
    (month < 10 ? "0" + month : month) +
    "." +
    (day < 10 ? "0" + day : day)
  );
};

const dateFormat = /^(\d{4})-(\d{2})-(\d{2})$/;

const showDateTime = (CT) => {
  const currentTime = Date.now();
  const timeMills = currentTime - Date.parse(CT);
  let time;
  if (timeMills < 60000) {
    time = `${Math.trunc(timeMills / 1000)}秒前`;
  } else if (timeMills < 3600000) {
    time = `${Math.trunc(timeMills / 60000)}分钟前`;
  } else if (timeMills < 86400000) {
    time = `${Math.trunc(timeMills / 3600000)}小时前`;
  } else if (timeMills >= 86400000) {
    time = formatDate(CT);
  }
  return time;
};

const ValidDate = (datestring) => {
  return dateFormat.test(datestring);
};
export { formatDate, showDateTime, ValidDate };
