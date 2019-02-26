const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/tips/regular/pushId').onWrite(event => {
  var pushId = event.params.pushId;
  console.log('Push notification triggered with id : '+ pushId);

  if (!event.data.val()) {
    console.log('Push notification deleted with id : '+ pushId);
  }

  var eventSnapshot = event.data.val();

  var topic = "regular";

  var payload = {
    notification : {
      title : eventSnapshot.title,
      body : eventSnapshot.status,
      sound : "default",
      icon : "default",
      click_action : "com.ke.siundu344.betshwari_TARGET_REGULAR"
    },
    data : {
    pushId : pushId,
    pushTitle : eventSnapshot.title,
    pushStatus : eventSnapshot.status,
    pushTips : eventSnapshot.tips
    }
  };

  const options = {
    priority : "high",
    timeToLive : 60 * 60 * 24
  };

  return admin.messaging().sendToTopic(topic, payload, options);

});
