document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('year').textContent = new Date().getFullYear();

    const contactForm = document.getElementById('contact-form');
    const formStatus = document.getElementById('form-status');
    // Assuming you are using <button type="button"> now
    const sendButton = contactForm.querySelector('button[type="button"]');

    let isSubmitting = false;

    // Use the button's click event to trigger the submission logic
    sendButton.addEventListener('click', async (e) => {
        e.preventDefault();

        if (!contactForm.checkValidity()) {
            contactForm.reportValidity();
            return;
        }

        if (isSubmitting) {
            return;
        }

        isSubmitting = true;
        sendButton.disabled = true;

        formStatus.textContent = 'Sending...';
        formStatus.style.color = 'var(--text-light)';

        const formData = {
            name: document.getElementById('name').value,
            email: document.getElementById('email').value,
            message: document.getElementById('message').value,
        };

        // **FIX HERE: DECLARE RESPONSE OUTSIDE TRY/CATCH**
        let response = null;

        try {
            response = await fetch('/api/contact', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                formStatus.textContent = 'Message sent successfully! Thank you.';
                formStatus.style.color = '#415a77';
                contactForm.reset();
                // We DON'T reset isSubmitting here on purpose (keeps button disabled)
            } else {
                throw new Error('Server responded with an error.');
            }
        } catch (error) {
            console.error('Form submission error:', error);
            formStatus.textContent = 'An error occurred. Please try again.';
            formStatus.style.color = '#ff4f4f';

            sendButton.disabled = false;
            isSubmitting = false;
        }
    });
});