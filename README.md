# AndroidHhttpClient

## Usage
Build an HttpCommand with DataModel and DataObject.

```java
HttpCommand iftttCommand = new HttpCommandBuilder()
                .setID(COMMAND_ID)
                .setURL(IFTTT_URL)
                .setMethod(HttpCommand.Method.POST)
                .setHeaders(HTTPHeader.getDefaultHeader())
                .setDataModel(IFTTTItem.class)
                .setDataObject(iftttItem, "value1")
                .build();
```

Using MailBox to send HttpCommand.
```java
MailBox.getInstance().deliverMail(context, iftttCommand, mIftttCallback);
```

Receive response or error from MailBox.
```java
public abstract class IFTTTCallback implements IMailReceiveCallback {
    private static final String TAG = IFTTTCallback.class.getSimpleName();

    @Override
    public void onMailReceive(AMailItem mailItem) {
        Log.d(TAG, "send IFTTT onMailReceive:");
        if (mailItem != null && mailItem instanceof IFTTTItem) {
            IFTTTItem iftttItem = (IFTTTItem) mailItem;

            // Checks if request is success
            if (ErrorCodeHandler.isSuccess(iftttItem.getStatus())) {
                onIftttReceived(iftttItem);
            } else {
                onFailed(iftttItem.getStatus(), iftttItem.getMessage());
            }
        } else {
            onFailed(ErrorCodeList.UNKNOWN_EXCEPTION.getCode(),
                    ErrorCodeList.UNKNOWN_EXCEPTION.toString());
        }
    }

    abstract public void onIftttReceived(IFTTTItem iftttItem);
    abstract public void onFailed(String status, String message);
}
```
